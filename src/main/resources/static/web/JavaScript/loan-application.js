const {createApp} = Vue

const app = createApp({
    data() {
        return {
            data: [],
            info: [],
            dataAccounts: [],
            sidebar: "close",
            inputTypeLoan: undefined,
            inputAmount: undefined,
            inputPayments: undefined,
            typeLoanPayments: undefined,
            inputAccountDestiny: undefined,
            allPayments: [],
            maxAmount: undefined,
            interestQuotaAmount: undefined,
            messageAlert: "",
        }
    },
    created() {
        this.loadLoans()
        this.loadAccounts()
    },
    methods: {
        loadLoans() {
            axios.get("/api/loans")
                .then(response => {
                    this.data = response.data
                    console.log(this.data);
                })
                .catch(err => console.log(err))
        },
        loadAccounts() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.info = response.data
                    this.dataAccounts = this.info.accounts
                    this.dataAccounts.sort((a,b)=>{
                        if(a.id < b.id){
                            return -1
                        }
                    });
                })
                .catch(err => console.log(err))
            },
            createLoan(){
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, make the Loan'
            })
            .then((result) => {
                if (result.isConfirmed) {
                    axios.post("/api/loans", {accountDestiny: this.inputAccountDestiny,  idloans: this.inputTypeLoan, amount: this.inputAmount, payments: this.inputPayments})
                    .then( () => { Swal.fire (
                        'Done successfully',
                        'Your Loans has been add.',
                        'success'
                    )
                    setTimeout( () => location.href = "/web/loan-application.html", 1500)
                    })
                    .catch(response => {
                        this.messageAlert = response.response.data
                        if(this.messageAlert == "Your Destiny Account doesn't exist"){
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "Your Destiny Account doesn't exist",
                            })
                        }
                        else if(this.messageAlert == "You can't have two loans of the same type"){
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "You can't have two loans of the same type",
                            })
                        }
                        else if(this.messageAlert == "The amount must be greater than $50000"){
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "The amount must be greater than $5000",
                            })
                        }
                        else if(this.messageAlert == "The amount requested is greater than the amount of the loan"){
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "The amount requested is greater than the amount of the loan",
                            })
                        }
                        else if(this.messageAlert == "Missing Amount"){
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "Missing Amount",
                            })
                        }
                        else if(this.messageAlert == "Incorrect Amount"){
                            Swal.fire({
                                icon: 'error',
                                title: 'Oops...',
                                text: "Incorrect Amount",
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
        filterSelectPayment(){
            this.typeLoanPayments = this.data.find(loan => loan.id == this.inputTypeLoan)
            if(this.typeLoanPayments){
                this.allPayments = this.typeLoanPayments.payments
                this.maxAmount = this.typeLoanPayments.maxAmount
            }
        },
        QuotaWithInteres(){
            this.interestQuotaAmount = this.inputAmount * 1.20 / this.inputPayments
        }
    }
})

app.mount('#app')