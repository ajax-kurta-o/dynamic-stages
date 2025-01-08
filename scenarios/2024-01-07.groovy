import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

steps = [
    [branch: "a", command: "echo 'This is branch a'"],
    [branch: "b", command: "echo 'This is branch b'"]
]

def getStages() {
    return [
        [
            name: "Deploy services",
            steps: [
                script {
                                    parallel steps.collectEntries { step ->
                                        [(step.branch): {
                                            sh step.command
                                        }]
                                    }
                                }
            ]
        ]
    ]
}

return this