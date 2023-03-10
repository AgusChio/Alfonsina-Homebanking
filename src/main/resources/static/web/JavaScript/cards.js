const {createApp} = Vue 

const app = createApp({
    data() {
        return {
            data: [],
            sidebar: "close",
            cardsData: [],
            debitsCards: [],
            creditsCards: [],
            card:[],
            active: true,
            dateExpiredString: "",
        }
    },
    created() {
        this.loadCards()
    },
    methods: {
        toggleNav(){
            if(this.sidebar == "close"){
                this.sidebar = ""
            } else {
                this.sidebar = "close"
            }
        },
        loadCards() {
            axios.get("/api/clients/current")
                .then(response => {
                    this.data = response.data
                    this.cardsData = this.data.cards
                    this.dateExpiredString = new Date().getFullYear.toString;
                    this.filterCards(this.cardsData)
                })
        },
        filterCards(cards) {
            this.creditsCards = cards.filter(card => card.type === 'CREDIT')
            this.debitsCards = cards.filter(card => card.type === 'DEBIT')
        },
        locationCard(){
            location.href="/web/create-cards.html"
        },
        deleteCard(id) {
			Swal.fire({
				title: "Do you wanna delete this account?",
				icon: "warning",
				showCancelButton: true,
				confirmButtonText: "Delete",
			}).then((result) => {
				if (result.isConfirmed) {
					axios.patch(`/api/clients/current/cards/${id}`)
						.then((response) => {
							Swal.fire("Deleted", "", "success");
							location.href = "/web/cards.html";
						})
						.catch(err => console.log(err));
				}
			});
		},
        logOut() {
            axios.post('/api/logout')
                .then(() => location.href = '/web/index.html')
        }
    }
})

app.mount('#app')