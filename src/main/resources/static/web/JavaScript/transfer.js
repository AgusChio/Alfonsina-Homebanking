const {createApp} = Vue

const app = createApp({
    data() {
        return {
            data: [],
            sidebar: "close",
            dataAccounts: [],
            dataNumberAccount:[],
            inputAccountOrigin: undefined,
            inputAccountDestiny: undefined,
            inputSelectAccount: undefined,
            inputAmount: undefined,
            inputDescription: "",
            selectedAccounts:[],
            messageAlert :"",
        }
    },
    created() {
        this.loadAccounts()
    },
    methods: {
        loadAccounts() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.data = response.data
                    this.dataAccounts = this.data.accounts
                    this.dataNumberAccount = this.dataAccounts.map(accounts => accounts.number)
                    this.dataAccounts.sort((a,b)=>{
                        if(a.id < b.id){
                            return -1
                        }
                    });
                })
                .catch(err => console.log(err))
        },
        createTransaction(){
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, do the transactions'
            })
            .then((result) => {
                if (result.isConfirmed) {
                    axios.post("/api/transactions", `amount=${this.inputAmount}&description=${this.inputDescription}&oAccount=${this.inputAccountOrigin}&dAccount=${this.inputSelectAccount}`)
                    .then( () => { Swal.fire (
                        'Done successfully',
                        'Your transaction has been send.',
                        'success'
                    )
                    setTimeout( () => location.href = "/web/transfers.html", 1500)
                    })
                    .catch(response => {
                        this.messageAlert = response.response.data
                        if(this.messageAlert == "Origin Account can't be the same as Destiny Account"){
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "Origin Account can't be the same as Destiny Account",
                            })
                        }
                        else if(this.messageAlert == "Not enough balance"){
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "Not enough balance",
                            })
                        }
                        else if(this.messageAlert == "Your Destiny Account doesn't exist"){
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "Your Destiny Account doesn't exist",
                            })
                        }
                        else{
                            Swal.fire({
                                icon: 'question',
                                title: 'Data Incomplete...',
                                text: "Please, check that all fields are completed.",
                            })
                        }
                    })
                }
            })
            .catch(err => console.log(err))
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
                .then(() => location.href = '/web/index.html'  )
        },
    },
    computed: {
        filterAccounts(){
            this.selectedAccounts = this.dataNumberAccount.filter( accounts => accounts !== this.inputAccountOrigin)
        }
    }
})

app.mount('#app')