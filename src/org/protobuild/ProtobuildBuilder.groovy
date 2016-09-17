package org.protobuild;

class ProtobuildBuilder {

  def steps

  ProtobuildBuilder(steps) {
    this.steps = steps
  }

  def buildProject(name, url) {
    steps.stage(name) {
      def platforms = [:]

      platforms['linux'] = {
        steps.stage(name + ": Linux") {
          steps.node('linux') {
            steps.timeout(30) {
              steps.checkout poll: true, scm: [$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: name], [$class: 'CleanCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '2a0f2367-7be2-419f-9b0f-b55c3305dea0', url: url]]]
              steps.sh ("cd " + name + " && mono Protobuild.exe --automated-build")
            }
          }
        }
      }

      platforms['mac'] = {
        steps.stage(name + ": Linux") {
          steps.node('mac') {
            steps.timeout(30) {
              steps.checkout poll: false, scm: [$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: name], [$class: 'CleanCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '2a0f2367-7be2-419f-9b0f-b55c3305dea0', url: url]]]
              steps.sh ("cd " + name + " && /usr/local/bin/mono Protobuild.exe --automated-build")
            }
          }
        }
      }

      platforms['windows'] = {
        steps.stage(name + ": Linux") {
          steps.node('windows') {
            steps.timeout(30) {
              steps.checkout poll: false, scm: [$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: name], [$class: 'CleanCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '2a0f2367-7be2-419f-9b0f-b55c3305dea0', url: url]]]
              steps.bat ('cd ' + name + ' && Protobuild.exe --automated-build')
            }
          }
        }
      }

      steps.parallel platforms
    }
  }

}