const {createApp} = Vue 

const app = createApp({
    data() {
        return {
            data:[],
            dataAccounts:[],
            accountSelected:"",
            inputTypeCard: undefined,
            inputColorCard: undefined,
            alert: '',
            error: undefined
        }
    },
    created() {
        this.loadData()
    },
    methods: {
        loadData(){
            axios.get('/api/clients/current')
                .then(response => {
                    this.data = response.data
                    this.dataAccounts = this.data.accounts
                })
        },
        createCards() {
            axios.post('/api/clients/current/cards', `type=${this.inputTypeCard}&color=${this.inputColorCard}&accountSelect=${this.accountSelected}`)
                .then(() => location.href = "/web/cards.html")
                .catch(response => {
                    this.error = response.response.status
                    if(this.error == 409) {
                        this.alert = 'The color of the cards cannot be repeated'
                    } else if (this.error == 403){
                        this.alert = 'Cannot create more cards'
                        setTimeout(() => this.alert = '', 3000)
                    } else {
                        this.alert = 'You must complete all fields'
                        setTimeout(() => this.alert = '', 3000)
                    }
                })
        },
    }
})
app.mount('#app')