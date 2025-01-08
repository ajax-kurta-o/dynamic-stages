import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

steps = [

]

def getStages() {
    return [
        [
            name: "Deploy services",
            stages: [

            ]
            steps: [
                [branch: "a", command: "echo 'This is branch a'"],
                [branch: "b", command: "echo 'This is branch b'"]
            ]
        ]
    ]
}

def performStages(stages) {
    stages.each { dynamicStage ->
                            stage(dynamicStage.name) {
                                dynamicStage.steps.each { step ->
                                    step.call()
                                }
                            }
                        }
}

return this