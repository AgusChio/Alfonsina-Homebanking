const {createApp} = Vue

const app = createApp({
    data() {
        return {
            data: [],
            sidebar: "close",
            cardHolderSelect: '',
            cardNumber: '',
            expDate: '',
            cvv: undefined,
            amount: undefined,
            accountSelected: undefined,
            description: '',
            accounts: [],
            accountCurrent: [],
            cardCurrent: [],
        }
    },
    created() {
        this.loadAccounts()
    },
    methods: {
        loadAccounts() {
            axios.get('/api/clients/current')
                .then(response => this.accounts = response.data.accounts)
                .catch(error => console.error(error))
        },
        makePay() {
            axios.post('/api/transactions/payments', {
                "cardHolder" : this.cardHolderSelect,
                "number" : this.cardNumber,
                "cvv": this.cvv,
                "thruDate": this.expDate,
                "amount" : this.amount,
                "description": this.description,
            })
                .then(() => {
                    Swal.fire(
                        'Payment made!',
                        'The amount of your account was deducted',
                        'success'
                    )
                    .then(() => window.location.reload())
                })
                .catch(() => {
                    if(this.cardHolderSelect == undefined || this.cardNumber == undefined || this.expDate === undefined || this.cvv === undefined || this.amount === undefined || this.description === undefined) {
                        Swal.fire({
                            icon: 'question',
                            title: 'Data incomplete',
                            text: 'Please, check that all fields are completed',
                        })
                    } else if(this.amount === 0) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: 'The amount is 0',
                        })
                    } else if(this.cvv === 0) {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: "The cvv can't be 0",
                        })
                    } else {
                        Swal.fire({
                            icon: 'error',
                            title: 'Oops...',
                            text: "Error data",
                        })
                    }
                })
            },
        toggleNav(){
            if(this.sidebar == "close"){
                this.sidebar = ""
            } else {
                this.sidebar = "close"
            }
        },
        logOut() {
            axios.post('/api/logout')
                .then(() => location.href = '/web/index.html')
        }
    },
    computed: {
        filterByAccount() {
            this.accountCurrent = this.accounts.find(prop => prop.number === this.accountSelected)
            if(this.accountCurrent) {
                this.cardCurrent = this.accountCurrent.cards
            }
        }
    }
})

app.mount('#app')