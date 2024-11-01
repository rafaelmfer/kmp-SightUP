//
//  CameraViewController.swift
//  iosApp
//
import UIKit
import AVFoundation

@objcMembers public class CameraViewController: UIViewController, AVCaptureMetadataOutputObjectsDelegate {
    var captureSession: AVCaptureSession!
    var previewLayer: AVCaptureVideoPreviewLayer!
    var distanceTimer: Timer?
    private var lastFaceMetadata: AVMetadataFaceObject?

    @objc public var onDistanceUpdate: ((String) -> Void)?
    @objc public func setDistanceUpdateCallback(_ callback: @escaping (String) -> Void) {
        self.onDistanceUpdate = callback
    }
    
    @objc override public func viewDidLoad() {
        super.viewDidLoad()
        
        captureSession = AVCaptureSession()
        captureSession.automaticallyConfiguresApplicationAudioSession = false
        
        guard let videoCaptureDevice = AVCaptureDevice.default(
            .builtInWideAngleCamera,
            for: .video,
            position: .front
        ) else { return }
        
        do {
            let videoInput = try AVCaptureDeviceInput(device: videoCaptureDevice)
            if captureSession.canAddInput(videoInput) {
                captureSession.addInput(videoInput)
            }
        } catch {
            return
        }
        
        previewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
        previewLayer.frame = view.layer.bounds
        previewLayer.videoGravity = .resizeAspectFill
        view.layer.addSublayer(previewLayer)
        
        setupMetadataOutput()

        // Run the session in a backgroun thread
        DispatchQueue.global(qos: .userInitiated).async {
            self.captureSession.startRunning()
        }
        
        startDistanceTimer()
    }
    
    private func setupMetadataOutput() {
        let metadataOutput = AVCaptureMetadataOutput()
        if captureSession.canAddOutput(metadataOutput) {
            captureSession.addOutput(metadataOutput)
            metadataOutput.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
            metadataOutput.metadataObjectTypes = [.face]
        }
    }
    
    private func startDistanceTimer() {
        distanceTimer = Timer.scheduledTimer(timeInterval: 5.0, target: self, selector: #selector(updateDistance), userInfo: nil, repeats: true)
    }
    
    @objc private func updateDistance() {
        // Call calculateDistance() periodically
        if let faceMetadata = getCurrentFaceMetadata() {
            let distanceText = calculateDistance(faceMetadata: faceMetadata)
            DispatchQueue.main.async {
                self.onDistanceUpdate?(distanceText)
            }
        }
    }
    
    public func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects: [AVMetadataObject], from connection: AVCaptureConnection) {
        if let faceMetadata = metadataObjects.first as? AVMetadataFaceObject {
            lastFaceMetadata = faceMetadata
            let distanceText = calculateDistance(faceMetadata: faceMetadata)
            DispatchQueue.main.async {
                self.onDistanceUpdate?(distanceText)
            }
        }
    }
    
    private func getCurrentFaceMetadata() -> AVMetadataFaceObject? {
        return lastFaceMetadata
    }
    
    private func calculateDistance(faceMetadata: AVMetadataFaceObject) -> String {
        let avgFaceWidthCm: CGFloat = 14.0
        let approximateFocalLengthPx: CGFloat = 600.0
        let correctionFactor: CGFloat = 2.5
        
        let faceWidthInPx = faceMetadata.bounds.width * view.bounds.width
        let distanceToFaceCm = (approximateFocalLengthPx * avgFaceWidthCm) / (faceWidthInPx * correctionFactor)
        
        return String(format: "%.2f", distanceToFaceCm)
    }
    
    @objc override public func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if !captureSession.isRunning {
            captureSession.startRunning()
        }
        startDistanceTimer()  // Restart the timer with the view
    }
    
    @objc override public func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        if captureSession.isRunning {
            captureSession.stopRunning()
        }
        stopDistanceTimer() 
    }
    
    private func stopDistanceTimer() {
        distanceTimer?.invalidate()
        distanceTimer = nil
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
        stopDistanceTimer()
    }
}
