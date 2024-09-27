//
//  CameraViewController.swift
//  iosApp
//
//  Created by Ceci on 2024-09-26.
//  Copyright © 2024 orgName. All rights reserved.
//
import UIKit
import AVFoundation

// AVCaptureMetadataOutput: to dectect metadata, such as faces
@objc public class CameraViewController : UIViewController, AVCaptureMetadataOutputObjectsDelegate {
    var captureSession : AVCaptureSession!
    var previewLayer : AVCaptureVideoPreviewLayer!
    
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
        // previewLayer.videoGravity = .resizeAspect
        view.layer.addSublayer(previewLayer)
        
        // COnfiguracion to detect the face
        // faces output
        let metadataOutput = AVCaptureMetadataOutput()
        if captureSession.canAddOutput(metadataOutput) {
            captureSession.addOutput(metadataOutput)
            
            metadataOutput.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
            metadataOutput.metadataObjectTypes = [.face] // Solo queremos detectar rostros
        }
        
        // Crear y configurar el UILabel para mostrar la distancia
//                distanceLabel = UILabel()
//                distanceLabel.translatesAutoresizingMaskIntoConstraints = false
//                distanceLabel.textColor = .white
//                distanceLabel.font = UIFont.boldSystemFont(ofSize: 24)
//                distanceLabel.text = "Calculando..."
//                view.addSubview(distanceLabel)
//                
//                // Configurar las restricciones de la etiqueta (centrada en la parte inferior)
//                NSLayoutConstraint.activate([
//                    distanceLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor),
//                    distanceLabel.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -50)
//                ])
        
        captureSession.startRunning()
        
    }
    
    @objc override public func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        captureSession.stopRunning()
    }
    
    // This function is called when a face is detected
    public func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects: [AVMetadataObject], from connection: AVCaptureConnection) {
        if let faceMetadata = metadataObjects.first as? AVMetadataFaceObject {
            
            // Get face bounds in screen coordinates
            let faceBounds = faceMetadata.bounds
            
            // Obtener el ancho de la capa de previsualización
            let previewLayerWidth = previewLayer.bounds.width
            
            // Calcular el factor de escala para el ancho y el alto de la previsualización
            let widthScaleFactor = previewLayerWidth / view.frame.size.width
            
            // Ajustar el ancho del rostro en pantalla
            let faceWidthOnScreenPx = faceBounds.width * view.frame.size.width / widthScaleFactor
            
            
            // Get the camera's focal length and pixel size
            if let videoCaptureDevice = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .front) {
                // Usar dimensiones conocidas del sensor y de la imagen
                let sensorWidthPx = CGFloat(videoCaptureDevice.activeFormat.formatDescription.dimensions.width)
                
                // Asumir el ancho físico del sensor (en mm), ajustar según el dispositivo
                let sensorWidthMm: CGFloat = 6.17 // Por ejemplo, para un iPhone común
                
                // Calcular el tamaño de píxel en milímetros
                let pixelSizeMmPx = sensorWidthMm / sensorWidthPx
                
                // Convertir el ancho del rostro en pantalla a milímetros
                let faceWidthOnScreenInMM = faceWidthOnScreenPx * pixelSizeMmPx
                
                // Acceder al campo de visión y calcular la distancia focal
                let fieldOfView = videoCaptureDevice.activeFormat.videoFieldOfView
                let sensorWidthInMm = CGFloat(sensorWidthMm / 2.0)
                let fieldOfViewRad = CGFloat((fieldOfView / 2) * .pi ) / 180.0
                let focalLengthMm = sensorWidthInMm / tan(fieldOfViewRad)
                
                // Ancho promedio de un rostro real (en cm)
                let realFaceWidthCm: CGFloat = 14.0
            
                // Calcular la distancia a la cámara
                let correctionFactor = CGFloat(6.5)
                let distanceToFace = (realFaceWidthCm * focalLengthMm) / (faceWidthOnScreenInMM * correctionFactor)
                
                // Actualizar la etiqueta con la distancia calculada
//                              DispatchQueue.main.async {
//                                  self.distanceLabel.text = String(format: "Distancia: %.2f cm", distanceToFace)
//                              }
//                let distanceFace2 = (realFaceWidthCm * focalLengthMm) / (faceWidthOnScreenInMM)
                print("Distancia focal en mm: \(focalLengthMm)")
                print("Ancho del rostro en pantalla (mm): \(faceWidthOnScreenInMM)")
                print("Distancia al rostro (cm): \(distanceToFace)")
            }
        }
    }
}
