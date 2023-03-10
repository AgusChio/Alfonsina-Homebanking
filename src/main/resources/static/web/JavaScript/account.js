const {createApp} = Vue

const app = createApp({
    data() {
        return {
            data: [],
            transactions: [],
            queryString: undefined,
            sidebar: "close",
            balance: undefined,
            yearStartSelected: '',
            yearEndSelected: '',
            account: ""
        }
    },
    created() {
        this.loadTransaction()
    },
    methods: {
        loadTransaction(){
            this.queryString = location.search;
            let params = new URLSearchParams(this.queryString);
            let id = params.get("id")
            axios.get("/api/accounts/" + id)
            .then(response => {
                this.data = response.data
                this.account = this.data.number
                this.balance = this.data.balance
                this.transactions = this.data.transactions;
                this.transactions.sort((a,b)=>{
                    if(a.id > b.id){
                        return -1
                    }
                });
            })
            .catch(err => console.log(err))
        },

        createPDF(){
            axios({
                url: "/api/generatePdf",
                method: "POST",
                data: {
                    startDate:this.yearStartSelected,
                    endDate:this.yearEndSelected,
                    accountNumber:this.account
                },
                responseType: "blob"
            })
            .then(response =>{
                const href = URL.createObjectURL(response.data)
                const link = document.createElement("a")
                link.href = href
                link.setAttribute("download", "transactions.pdf")
                document.body.appendChild(link)
                link.click()
                Swal.fire(
                    'One moment...download done!',
                    'Look at in the file with your transactions',
                    'success'
                )
            })
            .catch(() => {
                if(this.yearStartSelected > this.yearEndSelected) {
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'The start date cannot be greater than the end date',
                    })
                }
                else{
                    Swal.fire({
                        icon: 'error',
                        title: 'Oops...',
                        text: 'Please, fill both fields',
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
        }
    },
    computed: {
    }
})

app.mount('#app')