const {createApp} = Vue 

const app = createApp({
    data() {
        return {
            inputEmail: undefined,
            inputPassword:undefined,
            error: undefined,
            alert: ""
        }
    },
    created() {
    },
    methods: {
        loadSingIn() {
            axios.post('/api/login', `email=${this.inputEmail}&password=${this.inputPassword}`)
                .then(response => {
                    axios.get('/api/clients')
                        .then(response => location.href = '/web/manager.html')
                        .catch(err => location.href = '/web/accounts.html')
                })
                .catch(err => {
                    this.error = err.response.data.status
                    if(this.error == 401) {
                    this.alert = "The email or password are incorrect, please enter them correctly"
                    setTimeout(() => this.alert = " ", 3000)
                    }
                })
        }
    }
})

app.mount('#app')