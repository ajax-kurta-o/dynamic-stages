import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def getStages() {
    return [
        [
            name: "Deploy services",
            steps: [
                [branch: "a", command: "echo 'This is branch a'"],
                [branch: "b", command: "echo 'This is branch b'"]
            ]
        ]
    ]
}

def performStages(stages) {
    return {
        stages.each { dynamicStage ->
            stage(dynamicStage.name) {
                script {
                    parallel dynamicStage.steps.collectEntries { step ->
                        [(step.branch): {
                            sh step.command
                        }]
                    }
                }
            }
        }
    }
}

return this
