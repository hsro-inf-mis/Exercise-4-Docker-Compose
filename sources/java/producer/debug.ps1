
$payaraJar = "payara-micro.jar"
$payaraVersion = "5.183"

$env:RABBITMQ_HOST = "localhost"

if(-not (Test-Path $payaraJar)){
    $downloadPath = [io.path]::combine((Split-Path -Parent -Path $MyInvocation.MyCommand.Definition), $payaraJar)
    Write-Host $("Downloading Payara Micro to path " + $downloadPath)
    $client = New-Object System.Net.WebClient;
    $client.DownloadFile($("https://s3-eu-west-1.amazonaws.com/payara.fish/Payara+Downloads/" + $payaraVersion + "/payara-micro-" + $payaraVersion + ".jar"), $downloadPath);

    $client.Dispose()
}

gradle build
java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -jar $payaraJar --deploy $(Get-ChildItem .\build\libs\ -Filter *.war | % {$_.FullName} | Select-Object -first 1)