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
                    console.log(this.data)
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
            console.log(this.modal);
            if(this.modal == "modal__close"){
                this.modal = "modal--show"
            } else {
                this.modal = "modal__close"
            }
        },
        addAccount(){
            axios.post('/api/clients/current/accounts',`accountType=${this.accountType}`)
                .then(() => location.href = "/web/accounts.html")
        },
        deleteAccount(id){
            axios.patch(`/api/clients/current/accounts/${id}`)
            .then(() => {
                this.account = this.dataAccounts.filter(account => account.id == id);
                this.active = this.account[0].active_account;
                if (this.active){
                    this.active = false;
                }
                location.href = "/web/accounts.html"
            })
            .catch(err => console.log(err));
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