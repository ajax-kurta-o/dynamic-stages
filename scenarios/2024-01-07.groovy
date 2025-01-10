import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

def performStages() {
    return {
        def parallelStages1 = [
            [
                name: "Deploy services",
                stages: [
                    [stage_name: "deploy_a", steps: [ { -> sh "echo 'This is stage a'" } ]],
                    [stage_name: "deploy_b", steps: [ { -> sh "echo 'This is stage b'" } ]]
                ]
            ]
        ]

        parallel parallelStages1.collectEntries { dynamicStage ->
            [(dynamicStage.name): {
                dynamicStage.stages.each { stage ->
                    stage(stage.stage_name) {
                        stage.steps.each { step ->
                            step.call()
                        }
                    }
                }
            }]
        }

        stage('Run BDD Tests') {
            stage('Run Tests') {
                steps {
                    sh "echo 'Run tests'"
                }
            }
        }
    }
}

return this

