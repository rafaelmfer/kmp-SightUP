//
//  CameraViewController.swift
//  iosApp
//
//  Created by Ceci on 2024-09-26.
//  Copyright © 2024 orgName. All rights reserved.
//
import UIKit
import AVFoundation

@objc public class CameraViewController : UIViewController, AVCaptureMetadataOutputObjectsDelegate {
    var captureSession : AVCaptureSession!
    var previewLayer : AVCaptureVideoPreviewLayer!
    var distanceLabel: UILabel!
    
    // Define a callback for Kotlin
    //@objc public var onDistanceUpdate: (() -> String)?
    @objc public var onDistanceUpdate: ((String) -> Void)?
    
    @objc override public func viewDidLoad(){
        super.viewDidLoad()
        
        captureSession = AVCaptureSession()
        guard let videoCaptureDevice = AVCaptureDevice.default(.builtInWideAngleCamera ,for: .video, position: .front) else { return }
        let videoInput: AVCaptureDeviceInput
        
        do {
            videoInput = try AVCaptureDeviceInput(device: videoCaptureDevice)
        } catch {
            return
        }
        
        if (captureSession.canAddInput(videoInput)) {
            captureSession.addInput(videoInput)
        } else {
            return
        }
        
        previewLayer = AVCaptureVideoPreviewLayer(session: captureSession)
        previewLayer.frame = view.layer.bounds
        previewLayer.videoGravity = .resizeAspectFill
        view.layer.addSublayer(previewLayer)

        let metadataOutput = AVCaptureMetadataOutput()
        if captureSession.canAddOutput(metadataOutput) {
            captureSession.addOutput(metadataOutput)
            
            metadataOutput.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
            metadataOutput.metadataObjectTypes = [.face]
        }

        distanceLabel = UILabel()
        distanceLabel.translatesAutoresizingMaskIntoConstraints = false
        distanceLabel.textColor = .white
        distanceLabel.font = UIFont.boldSystemFont(ofSize: 24)
        distanceLabel.text = "Calculando..."
        view.addSubview(distanceLabel)

        NSLayoutConstraint.activate([
            distanceLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            distanceLabel.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -50)
        ])
        
        captureSession.startRunning()

        // Set a default callback, even if it's just a placeholder
        onDistanceUpdate = { _ in /* No-op or log */ }
    }
    
    @objc override public func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        captureSession.stopRunning()
    }
    

    public func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects: [AVMetadataObject], from connection: AVCaptureConnection) {
        if let faceMetadata = metadataObjects.first as? AVMetadataFaceObject {
            
            let faceBounds = faceMetadata.bounds

            let previewLayerWidth = previewLayer.bounds.width

            let widthScaleFactor = previewLayerWidth / view.frame.size.width

            let faceWidthOnScreenPx = faceBounds.width * view.frame.size.width / widthScaleFactor

            if let videoCaptureDevice = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .front) {

                let sensorWidthPx = CGFloat(videoCaptureDevice.activeFormat.formatDescription.dimensions.width)

                let sensorWidthMm: CGFloat = 6.17  // Average size for an iphone

                let pixelSizeMmPx = sensorWidthMm / sensorWidthPx

                let faceWidthOnScreenInMM = faceWidthOnScreenPx * pixelSizeMmPx

                let fieldOfView = videoCaptureDevice.activeFormat.videoFieldOfView
                let sensorWidthInMm = CGFloat(sensorWidthMm / 2.0)
                let fieldOfViewRad = CGFloat((fieldOfView / 2) * .pi ) / 180.0
                let focalLengthMm = sensorWidthInMm / tan(fieldOfViewRad)

                let realFaceWidthCm: CGFloat = 14.0

                let correctionFactor = CGFloat(6.5)
                let distanceToFace = (realFaceWidthCm * focalLengthMm) / (faceWidthOnScreenInMM * correctionFactor)
                
                let distanceText = String(format: "Distancia: %.2f cm", distanceToFace)
                DispatchQueue.main.async {
                    self.distanceLabel.text = String(format: "Distance: %.2f cm", distanceToFace)
                    self.onDistanceUpdate?(distanceText)
                }
            }
        }
    }
}
