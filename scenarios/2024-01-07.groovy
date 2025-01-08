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

return this