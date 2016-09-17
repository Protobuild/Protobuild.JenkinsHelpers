import org.protobuild.ProtobuildBuilder

def call(name, url) {
  def builder = new ProtobuildBuilder(steps)
  builder.buildProject(name, url)
}