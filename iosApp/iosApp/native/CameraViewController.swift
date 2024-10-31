//
//  CameraViewController.swift
//  iosApp
//

import UIKit
import AVFoundation

@objcMembers public class CameraViewController: UIViewController, AVCaptureMetadataOutputObjectsDelegate {
    var captureSession: AVCaptureSession!
    var previewLayer: AVCaptureVideoPreviewLayer!
    
    @objc public var onDistanceUpdate: ((String) -> Void)?  // Callback para enviar distancia a Kotlin
    
    // Nueva función para asignar el callback desde Kotlin
   @objc public func setDistanceUpdateCallback(_ callback: @escaping (String) -> Void) {
       self.onDistanceUpdate = callback
   }
    
    @objc override public func viewDidLoad() {
        super.viewDidLoad()
        
        captureSession = AVCaptureSession()
        // Desactivar configuración de audio automática para evitar interrupciones
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
        
        captureSession.startRunning()
    }
    
    private func setupMetadataOutput() {
        let metadataOutput = AVCaptureMetadataOutput()
        if captureSession.canAddOutput(metadataOutput) {
            captureSession.addOutput(metadataOutput)
            metadataOutput.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
            metadataOutput.metadataObjectTypes = [.face]
        }
    }
    
    public func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects:
                               [AVMetadataObject], from connection: AVCaptureConnection) {
        if let faceMetadata = metadataObjects.first as? AVMetadataFaceObject {
            let distanceText = calculateDistance(faceMetadata: faceMetadata)
            
            DispatchQueue.main.async {
                self.onDistanceUpdate?(distanceText)
            }
        }
    }
    
    
    private func calculateDistance(faceMetadata: AVMetadataFaceObject) -> String {
        let avgFaceWidthCm: CGFloat = 14.0
        let approximateFocalLengthPx: CGFloat = 600.0   píxeles para simplificar
        let correctionFactor : CGFloat = 3.0
        
        let faceWidthInPx = faceMetadata.bounds.width * view.bounds.width
        
        let distanceToFaceCm = (approximateFocalLengthPx * avgFaceWidthCm) / (faceWidthInPx * correctionFactor)
        
        return String(format: "%.2f", distanceToFaceCm)
    }
    
    
    //    private func calculateDistance(faceMetadata: AVMetadataFaceObject) -> String {
    //
    //        let realFaceWidthCm: CGFloat = 14.0
    //        let sensorWidthMm: CGFloat = 6.17     //  iPhones
    //        let correctionFactor: CGFloat = 5.0   //
    //
    //        guard let videoCaptureDevice = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .front) else {
    //            return "Unknown"
    //        }
    //
    //        // Obtener dimensiones del sensor en píxeles
    //        let sensorWidthPx = CGFloat(videoCaptureDevice.activeFormat.formatDescription.dimensions.width)
    //        let pixelSizeMmPx = sensorWidthMm / sensorWidthPx  // Tamaño de píxel en mm
    //
    //        // Ancho de rostro en pantalla convertido a mm
    //        let faceWidthOnScreenPx = faceMetadata.bounds.width * view.bounds.width
    //        let faceWidthOnScreenMm = faceWidthOnScreenPx * pixelSizeMmPx
    //
    //        // Calcular la longitud focal en mm usando el campo de visión (FOV) de la cámara
    //        let fieldOfView = videoCaptureDevice.activeFormat.videoFieldOfView
    //        let sensorWidthInMm = sensorWidthMm / 2.0
    //        let fieldOfViewRad = CGFloat((fieldOfView / 2) * .pi ) / 180.0
    //
    //        let focalLengthMm = sensorWidthInMm / tan(fieldOfViewRad)
    //
    //        // Calcular la distancia al rostro
    //        let distanceToFaceCm = (realFaceWidthCm * focalLengthMm) / (faceWidthOnScreenMm * correctionFactor)
    //
    //        return String(format: "%.2f", distanceToFaceCm)
    //    }
    
    
    @objc override public func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        if !captureSession.isRunning {
            captureSession.startRunning()
            print("Swift: Camera session started running")
        }
    }
    @objc override public func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        if captureSession.isRunning {
            captureSession.stopRunning()
            print("Swift: Camera session stopped running")
        }
    }
}
