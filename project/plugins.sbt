addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.1.0-M4")

addSbtPlugin("org.scoverage"      % "sbt-scoverage"       % "1.5.1")
addSbtPlugin("com.typesafe.sbt"   % "sbt-native-packager" % "1.3.4")
addSbtPlugin("org.wartremover"    % "sbt-wartremover"     % "2.2.1")
addSbtPlugin("com.github.gseitz"  % "sbt-release"         % "1.0.6")
addSbtPlugin("com.thesamet"       % "sbt-protoc"          % "0.99.18")
addSbtPlugin("com.twitter"        % "scrooge-sbt-plugin"  % "18.5.0")
addSbtPlugin("com.typesafe.sbt"   % "sbt-twirl"           % "1.3.13")

//libraryDependencies += "com.trueaccord.scalapb" %% "compilerplugin" % "0.7.4"

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.7.4"

