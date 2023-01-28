const {createApp} = Vue 

const app = createApp({
    data() {
        return {
            data:[],
            inputFirstName: "",
            inputLastName: "",
            inputEmail: "",
            inputPassword: "",
            status: undefined,
            messageAlert: " ",
        }
    },
    created() {
    },
    methods: {
        loadSignUp() {
            axios.post('/api/clients', `firstName=${this.inputFirstName}&lastName=${this.inputLastName}&email=${this.inputEmail}&password=${this.inputPassword}`)
            .then(() => {
                axios.post('/api/login', `email=${this.inputEmail}&password=${this.inputPassword}`)
                .then(() => {
                    axios.post('/api/clients')
                        .then(() => location.href = '/web/manager.html')
                        .catch(() => location.href = '/web/accounts.html')
                })
            })
            .catch(response => {
                console.log(response);
                this.messageAlert = response.response.data
                if(this.messageAlert == "Email already in use"){
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: "Email already in use",
                    })
                }
                else if(this.messageAlert == "Register Successfully"){
                    Swal.fire({
                        icon: 'success',
                        text: "Register Successfully",
                    })
                    setTimeout(() => this.alert = " ", 3000)
                } else{
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: "Missing Data",
                    })
                }
            })
    },
},
})
app.mount("#app")