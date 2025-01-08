
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
    ]
}

return this