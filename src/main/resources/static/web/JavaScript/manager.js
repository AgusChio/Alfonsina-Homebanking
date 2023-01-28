const { createApp } = Vue

    createApp({
        data() {
        return {
            clients: [],
            client: {},
            json: {},
            inputFirstName: "",
            inputLastName: "",
            inputemail: "",
            inputFirstNameModal:"",
            inputLastNameModal:"",
            inputEmailModal:"",
            idClient: undefined,
            newClient:{},
            nameLoan: '',
            maxAmount: undefined,
            paymentArraySelected:[],
            interesPercentaje: undefined
        }
    },
    created(){
        this.loadData();
    },
    methods:{
        loadData(){
            axios.get("/rest/clients")
            .then(result =>{
                this.json = result.data;
                this.clients = result.data._embedded.clients;
            })
            .catch(err => console.log(err));
        },

        addClient(){
            if(this.inputFirstName !== "" && this.inputLastName !== "" &&  this.inputemail !== ""){
                this.client = {
                    firstName: this.inputFirstName,
                    lastName: this.inputLastName,
                    email: this.inputemail,
                };
                Swal.fire({
                    icon: 'success',
                    title: 'The Client has been saved',
                    showConfirmButton: false,
                    timer: 1500
                })
                this.postClients(this.client);
            } else if(this.inputFirstName == "" && this.inputLastName == "" &&  this.inputemail == ""){
                Swal.fire({
                    icon: 'warning',
                    title: 'Oops...',
                    text: 'Please complete the form',
                })
            }
        },

        deleteClients(id){
            let byeClientId = id._links.self.href
            axios.delete(byeClientId)
            .then(()=> this.loadData())
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                confirmButtonColor: '#3085d6',
                confirmButtonText: 'Yes, delete it!'
            }).then((id) => {
                if (id.isConfirmed) {
                    Swal.fire(
                        'Deleted!',
                        'Your file has been deleted.',
                        'success'
                    )
                }
            })
            .catch(err => console.log(err));
        },

        newLoan() {
            if(this.maxAmount == undefined || this.nameLoan == '' || this.paymentArraySelected.length == 0) {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: 'Something went wrong!',
                })
            } else {
                let paymentsSelected = this.paymentArraySelected.map(prop => parseInt(prop))
                console.log(paymentsSelected);
                axios.post('/api/loans/admin',`name=${this.nameLoan}&maxAmount=${this.maxAmount}&payments=${paymentsSelected}&interest_percentaje=${this.interesPercentaje}`)
                    .then(() => {
                        Swal.fire(
                            'Loan Created!',
                            'Loan available for everyone!',
                            'success'
                        )
                    })
                    .catch(error => console.error(error))
                }
        },

        editClient(client) {
            this.idClient = client._links.self.href
            this.inputFirstNameModal = client.firstName
            this.inputLastNameModal = client.lastName
            this.inputEmailModal = client.email
        },

        saveNewClient(){
            axios.put(this.idClient , 
                newClient = {
                    firstName: this.inputFirstNameModal,
                    lastName: this.inputLastNameModal,
                    email: this.inputEmailModal
                })
            .then(() => this.loadData())
            .catch(err => console.log(err));
        },

        postClients(client){
            axios.post("/rest/clients", client)
            .then(()=> this.loadData())
            .catch(err => console.log(err));
        },

        logOut() {
            axios.post('/api/logout')
                .then(() => location.href = '/web/index.html')
        }
    }

    }).mount('#app')