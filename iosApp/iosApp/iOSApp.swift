import SwiftUI
import Contacts
import ComposeApp


class AppDelegate: NSObject, UIApplicationDelegate,PermissionRequestProtocol {

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil
    ) -> Bool {

    
    KoinIosKt.doInitKoinIos()

    PermissionBridge_iosKt.registerPermissionHandler(listener: self)

    return true
    }

    func requestContactPermission(callback: PermissionResultCallback) {
            let store = CNContactStore()
            switch CNContactStore.authorizationStatus(for: .contacts) {
            case .notDetermined:
                store.requestAccess(for: .contacts) { granted, error in
                    DispatchQueue.main.async {
                        if granted {
                            callback.onPermissionGranted()
                            print("Contacts permission granted")
                        } else {
                            callback.onPermissionDenied(isPermanentDenied: false)
                            print("Contacts permission denied")
                        }
                    }
                }
            case .denied:
                print("Contacts access is denied")
                DispatchQueue.main.async {
                    callback.onPermissionDenied(isPermanentDenied: true)
                }
            case .restricted:
                print("Contacts access is restricted")
                DispatchQueue.main.async {
                    callback.onPermissionDenied(isPermanentDenied: true)
                }
            case .authorized:
                print("Contacts access already authorized")
                DispatchQueue.main.async {
                    callback.onPermissionGranted()
                }
            @unknown default:
                fatalError("Unknown authorization status")
            }
        }

    func isContactPermissionGranted() -> Bool {
        let status = CNContactStore.authorizationStatus(for: .contacts)
        return status == .authorized
    }


    }


@main
struct iOSApp: App {
    
  @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
