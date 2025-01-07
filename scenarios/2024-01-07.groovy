import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def getStages() {

    return [
        [
            name: "Deploy services",
            steps: {
                script {
                    parallel(
                        a: {
                            echo "This is branch a"
                        },
                        b: {
                            echo "This is branch b"
                        }
                    )
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