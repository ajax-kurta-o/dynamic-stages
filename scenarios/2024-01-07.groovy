import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def getStages() {

    return [
        [
            name: "Deploy services",
            steps: {
                parallel: {
                    stage("Deploy a911-svc 1.118.0-7447.RELEASE") {
                        agent any
                        steps {
                            echo "In deploy a911-svc"
                        }
                    }
                    stage("Deploy mobile-gw-svc 1.29.0-8984.RELEASE") {
                        agent any
                        steps {
                            echo "In deploy mobile-gw-svc"
                        }
                    }
                }
            }
        ],
        [
            name: "Run BDD tests",
            steps: {
                script {
                    echo "In BDD Tests"

                }
            }
        ],
    ]
}

return this