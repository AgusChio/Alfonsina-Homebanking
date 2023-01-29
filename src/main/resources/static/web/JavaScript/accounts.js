const {createApp} = Vue

const app = createApp({
    data() {
        return {
            data: [],
            dataAccounts: [],
            clientLoans: [],
            sidebar: "close",
            account: [],
            active: true,
            accountType: "",
            modal:"modal__close",
            alert: "",
        }
    },
    created() {
        this.loadClient()
    },
    methods: {
        loadClient() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.data = response.data
                    this.clientLoans = this.data.loans
                    this.dataAccounts = this.data.accounts
                    this.dataAccounts.sort((a,b)=>{
                        if(a.id < b.id){
                            return -1
                        }
                    });
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
        showModal(){
            if(this.modal == "modal__close"){
                this.modal = "modal--show"
            } else {
                this.modal = "modal__close"
            }
        },
        addAccount(){
            axios.post('/api/clients/current/accounts',`accountType=${this.accountType}`)
                .then(() => location.href = "/web/accounts.html")
                .catch(response => {
                    this.error = response.response.status
                    if(this.error == 400) {
                        this.alert = 'Please choose account type'
                        setTimeout(() => this.alert = '', 4000)
                    }
                })
        },
        deleteAccount(id) {
			Swal.fire({
				title: "Do you wanna delete this account?",
				icon: "warning",
				showCancelButton: true,
				confirmButtonText: "Delete",
			}).then((result) => {
				if (result.isConfirmed) {
					axios.patch(`/api/clients/current/accounts/${id}`)
						.then((response) => {
							Swal.fire("Deleted", "", "success");
							location.href = "/web/accounts.html";
						})
						.catch(response => {
                            this.messageAlert = response.response.data
                            if(this.messageAlert == "Cannot delete a account with balance"){
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: "Cannot delete a account with balance",
                                })
                            } else if(this.messageAlert = "Cannot delete a account with contain cards"){
                                Swal.fire({
                                    icon: 'error',
                                    title: 'Oops...',
                                    text: "Cannot delete a account with contain cards",
                                })
                            }
						});
				}
			});
		},
        locationLoan(){
            location.href="/web/loan-application.html"
        },
        logOut() {
            axios.post('/api/logout')
                .then(() => location.href = '/web/index.html')
        }
    }
})

app.mount('#app')