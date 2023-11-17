def remoteServer = env.REMOTE_SERVER
def remoteFilePath = env.REMOTE_FILE_PATH_SERVER
def containerName = env.CONTAINER_NAME
def imagesName = env.IMAGES_NAME
def ports = env.PORTS
node{
    stage("Pull code"){
        withCredentials([file(credentialsId: 'ssh-key', variable: 'ssh_key_file')]) {
            sh "cat $ssh_key_file > key"
            sh "chmod 400 key"
            sh" ssh -o StrictHostKeyChecking=no -i key $remoteServer 'cd Elearning-BE && git pull origin master'"
        }
    }
    stage("Build maven"){
        withCredentials([file(credentialsId: 'ssh-key', variable: 'ssh_key_file')]) {
            sh "cat $ssh_key_file > key"
            sh "chmod 400 key"
            sh" ssh -o StrictHostKeyChecking=no -i key $remoteServer 'cd Elearning-BE && mvn clean install'"
        }
    }
    stage("Stop container"){
        withCredentials([file(credentialsId: 'ssh-key', variable: 'ssh_key_file')]) {
            sh "cat $ssh_key_file > key"
            sh "chmod 400 key"
            script {
                // Kiểm tra xem container có tồn tại (bất kể đang chạy hay không)
                def isContainerExists = sh(
                script: "ssh -o StrictHostKeyChecking=no -i key $remoteServer 'docker ps -a -q -f name=$containerName'",
                returnStatus: true) == 0

                if (isContainerExists) {
                    sh "ssh -o StrictHostKeyChecking=no -i key $remoteServer 'docker stop $containerName'"
                    sh "ssh -o StrictHostKeyChecking=no -i key $remoteServer 'docker rm -f $containerName'"
                    sh "ssh -o StrictHostKeyChecking=no -i key $remoteServer 'docker rmi $imagesName'"
                } else {
                    echo "Container $containerName does not exist."
                }
            }
        }
    }
    stage("Build docker"){
        withCredentials([file(credentialsId: 'ssh-key', variable: 'ssh_key_file')]) {
            sh "cat $ssh_key_file > key"
            sh "chmod 400 key"
            sh" ssh -o StrictHostKeyChecking=no -i key $remoteServer 'cd Elearning-BE && docker build -t $imagesName .'"
        }
    }
    stage("Deploy"){
        withCredentials([file(credentialsId: 'ssh-key', variable: 'ssh_key_file')]) {
            sh "cat $ssh_key_file > key"
            sh "chmod 400 key"
            withCredentials([file(credentialsId: 'env', variable: 'envFile')]) {
                script {
                    def secretFileContent = sh(script: "cat $envFile", returnStdout: true).trim()
                    sh "echo '''$secretFileContent''' | ssh -o StrictHostKeyChecking=no -i key $remoteServer 'cat > $remoteFilePath'"
                }
            }
            sh "ssh -o StrictHostKeyChecking=no -i key $remoteServer 'docker run -d -v /root/upload/:/tmp/upload --env-file $remoteFilePath -p $ports --name $containerName $imagesName'"
        }
    }
}