var app = new Vue({
    el: '#app',
    data() {
        return {
            clientSheets : []
        }
    },
    mounted() {
        this.loadClientSheets();
    },
    methods: {
        loadClientSheets() {
            axios.get('/salesRep/client_sheets')
                .then(response => {
                    this.clientSheets = response.data.data;
                });
        },

        removeClientSheet(clientSheets) {
            axios.post('/salesRep/client_sheets/remove', clientSheets)
                .then(response => {
                    if (response.data.success) {
                        this.loadClientSheets();
                    }
                })
        },
    }
});