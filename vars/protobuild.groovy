import org.protobuild.ProtobuildBuilder

def call() {
  stage("Linux") {
    node('linux') {
      timeout(30) {
        checkout poll: true, changelog: true, scm: scm
        sh ("mono Protobuild.exe --upgrade-all")
        sh ("mono Protobuild.exe --automated-build")
      }
    }
  }

  stage("Mac") {
    node('mac') {
      timeout(30) {
        checkout poll: false, changelog: false, scm: scm
        sh ("/usr/local/bin/mono Protobuild.exe --upgrade-all")
        sh ("/usr/local/bin/mono Protobuild.exe --automated-build")
      }
    }
  }
    
  stage("Windows") {
    node('windows') {
      timeout(30) {
        checkout poll: false, changelog: false, scm: scm
        bat ("Protobuild.exe --upgrade-all")
        bat ('Protobuild.exe --automated-build')
      }
    }
  }
}