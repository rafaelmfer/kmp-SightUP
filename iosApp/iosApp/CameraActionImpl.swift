//
//  CameraActionImpl.swift
//  iosApp
//
//  Created by Ceci on 2024-09-25.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

import AVFoundation
import Vision
import UIKit

class CameraActionImpl: NSObject, AVCaptureVideoDataOutputSampleBufferDelegate {
    
    private var captureSession: AVCaptureSession?
    private var faceDetectionRequest: VNRequest?
    private var cameraOutput: AVCaptureVideoDataOutput?
    
    @objc func startCamera() {
        captureSession = AVCaptureSession()
        guard let device = AVCaptureDevice.default(.builtInWideAngleCamera, for: .video, position: .front) else {
            print("No front camera available")
            return
        }
        
        do {
            let input = try AVCaptureDeviceInput(device: device)
            if captureSession?.canAddInput(input) == true {
                captureSession?.addInput(input)
            } else {
                print("Unable to add input")
                return
            }
            
            // Configurar la detección de rostros
            setupFaceDetection()
            
            // Configurar salida de video para procesamiento en tiempo real
            cameraOutput = AVCaptureVideoDataOutput()
            cameraOutput?.setSampleBufferDelegate(self, queue: DispatchQueue.main)
            
            if captureSession?.canAddOutput(cameraOutput!) == true {
                captureSession?.addOutput(cameraOutput!)
            }
            
            captureSession?.startRunning()
        } catch {
            print("Error setting up camera input: \(error)")
        }
    }
    
    @objc func stopCamera() {
        captureSession?.stopRunning()
    }
    
    private func setupFaceDetection() {
        faceDetectionRequest = VNDetectFaceRectanglesRequest { [weak self] request, error in
            if let error = error {
                print("Face detection error: \(error)")
                return
            }
            
            guard let results = request.results as? [VNFaceObservation],
                  let face = results.first else {
                return
            }
            
            let faceBoundingBox = face.boundingBox
            self?.calculateDistanceFromFace(boundingBox: faceBoundingBox)
        }
    }
    
    // Método de cálculo de distancia desde la cara
    private func calculateDistanceFromFace(boundingBox: CGRect) {
        let faceWidthRatio = boundingBox.width
        let realFaceWidthCm: CGFloat = 15.0
        let estimatedDistanceCm = realFaceWidthCm / faceWidthRatio
        print("Estimated Distance: \(estimatedDistanceCm) cm")
    }
    
    // Delegate para procesamiento de buffer de video
    func captureOutput(_ output: AVCaptureOutput, didOutput sampleBuffer: CMSampleBuffer, from connection: AVCaptureConnection) {
        // Aquí se puede procesar cada frame del video
        guard let pixelBuffer = CMSampleBufferGetImageBuffer(sampleBuffer) else { return }
        
        // Crear un handler para procesar la detección de rostros
        let requestHandler = VNImageRequestHandler(cvPixelBuffer: pixelBuffer, options: [:])
        do {
            try requestHandler.perform([faceDetectionRequest!])
        } catch {
            print("Error processing face detection: \(error)")
        }
    }
}
