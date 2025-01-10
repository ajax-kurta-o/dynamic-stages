def perform() {
    stage('Non-Parallel Stage') {
        steps {
            echo 'This stage will be executed first.'
        }
    }
    stage('Parallel Stage') {
        failFast true
        parallel {
            stage('Branch A') {
                agent any
                steps {
                    echo "On Branch A"
                }
            }
            stage('Branch B') {
                agent any
                steps {
                    echo "On Branch B"
                }
            }
            stage('Branch C') {
                agent any
                stages {
                    stage('Nested 1') {
                        steps {
                            echo "In stage Nested 1 within Branch C"
                        }
                    }
                    stage('Nested 2') {
                        steps {
                            echo "In stage Nested 2 within Branch C"
                        }
                    }
                }
            }
        }
    }
}

return this