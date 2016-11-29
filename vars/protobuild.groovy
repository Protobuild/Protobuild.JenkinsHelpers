import org.protobuild.ProtobuildBuilder

def call() {  
  stage("Windows") {
    node('windows') {
      checkout poll: false, changelog: false, scm: scm
      bat ("Protobuild.exe --upgrade-all")
      bat ('Protobuild.exe --automated-build')
      stash includes: '*.nupkg', name: 'windows-packages'
    }
  }

  stage("Mac") {
    node('mac') {
      checkout poll: false, changelog: false, scm: scm
      sh ("/usr/local/bin/mono Protobuild.exe --upgrade-all")
      sh ("/usr/local/bin/mono Protobuild.exe --automated-build")
      stash includes: '*.nupkg', name: 'mac-packages'
    }
  }

  stage("Linux") {
    node('linux') {
      checkout poll: true, changelog: true, scm: scm
      sh ("mono Protobuild.exe --upgrade-all")
      sh ("mono Protobuild.exe --automated-build")
      stash includes: '*.nupkg', name: 'linux-packages'
    }
  }

  stage("Unified") {
    node('linux') {
      // Ensure a seperate working directory to the normal linux node above.
      ws {
        checkout poll: true, changelog: true, scm: scm
        if (fileExists('unified.build')) {
          sh ("mono Protobuild.exe --upgrade-all")
          unstash 'windows-packages'
          unstash 'mac-packages'
          unstash 'linux-packages'
          sh ("mono Protobuild.exe --automated-build unified.build")
        }
      }
    }
  }
}