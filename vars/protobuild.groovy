import org.protobuild.ProtobuildBuilder

def call(name, url) {
  //stage(name) {
    //def platforms = [:]

    //platforms['linux'] = {
    stage(name + ": Linux") {
      node('linux') {
        timeout(30) {
          checkout poll: true, changelog: true, scm
          sh ("cd " + name + " && mono Protobuild.exe --automated-build")
        }
      }
    }
    //}

    //platforms['mac'] = {
    stage(name + ": Mac") {
      node('mac') {
        timeout(30) {
          checkout poll: false, changelog: false, scm
          sh ("cd " + name + " && /usr/local/bin/mono Protobuild.exe --automated-build")
        }
      }
    }
    //}

    //platforms['windows'] = {
    stage(name + ": Windows") {
      node('windows') {
        timeout(30) {
          checkout poll: false, changelog: false, scm
          bat ('cd ' + name + ' && Protobuild.exe --automated-build')
        }
      }
    }
    //}

    //parallel platforms
  //}
}