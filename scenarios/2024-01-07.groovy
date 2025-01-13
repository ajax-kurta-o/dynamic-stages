import org.jenkinsci.plugins.pipeline.modeldefinition.Utils

// def performStages() {
//     def parallelStages1 = [
//         [
//             name: "Deploy services",
//             stages: [
//                 [stage_name: "deploy_a", steps: [ { -> sh "echo 'This is stage a'" } ]],
//                 [stage_name: "deploy_b", steps: [ { -> sh "echo 'This is stage b'" } ]]
//             ]
//         ]
//     ]
//
//     def run1 = [
//         [
//             name: "Run BDD tests",
//             stages: [
//                 [stage_name: "Run tests", steps: [ { -> sh "echo 'Run tests'" } ]]
//             ]
//         ]
//     ]
//
//     return {
//         parallelStages1.each { dynamicStage ->
//             stage(dynamicStage.name) {
//                 script {
//                     parallel dynamicStage.stages.collectEntries { stage ->
//                         [(stage.stage_name): {
//                             stage.steps.each { step ->
//                                 step.call()
//                             }
//                         }]
//                     }
//                 }
//             }
//         }
//     }
// }

def number() {
    return 2
}

def deployNumber() {
    return [2, 5]
}


def performStages() {

    return [
        [
            name: "Deploy a911-svc 1.118.0-7447.RELEASE",
            steps: {
                sh 'echo This is deploy a911'
            }
        ],
        [
            name: "Deploy mobile-svc 1.118.0-7447.RELEASE",
            steps: {
                sh 'echo This is deploy mobile'
            }
        ],
        [
            name: "Run BDD tests",
            steps: {
                sh 'echo This is tests'
            }
        ],

            name: "Deploy a911-svc2 1.118.0-7447.RELEASE",
            steps: {
                sh 'echo This is deploy a9112'
            }
        ],
        [
            name: "Deploy mobile-svc2 1.118.0-7447.RELEASE",
            steps: {
                sh 'echo This is deploy mobile2'
            }
        ],
        [
            name: "Run BDD tests2",
            steps: {
                sh 'echo This is tests2'
            }
        ],
    ]
}

return this


