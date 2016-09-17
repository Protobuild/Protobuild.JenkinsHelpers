import org.protobuild.ProtobuildBuilder

def call(name, url) {
  //stage(name) {
    //def platforms = [:]

    //platforms['linux'] = {
    stage(name + ": Linux") {
      node('linux') {
        timeout(30) {
          checkout poll: true, scm: [$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: name], [$class: 'CleanCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '2a0f2367-7be2-419f-9b0f-b55c3305dea0', url: url]]]
          sh ("cd " + name + " && mono Protobuild.exe --automated-build")
        }
      }
    }
    //}

    //platforms['mac'] = {
    stage(name + ": Mac") {
      node('mac') {
        timeout(30) {
          checkout poll: false, scm: [$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: name], [$class: 'CleanCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '2a0f2367-7be2-419f-9b0f-b55c3305dea0', url: url]]]
          sh ("cd " + name + " && /usr/local/bin/mono Protobuild.exe --automated-build")
        }
      }
    }
    //}

    //platforms['windows'] = {
    stage(name + ": Windows") {
      node('windows') {
        timeout(30) {
          checkout poll: false, scm: [$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: name], [$class: 'CleanCheckout']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '2a0f2367-7be2-419f-9b0f-b55c3305dea0', url: url]]]
          bat ('cd ' + name + ' && Protobuild.exe --automated-build')
        }
      }
    }
    //}

    //parallel platforms
  }
}