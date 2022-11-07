var app = new Vue({
    el: '#app',
    data() {
        return {
            newClientSheet : {}
        }
    },
    mounted() {
    },
    methods: {
        createClientSheet() {
            axios.post('/salesRep/createclient_sheet/create', this.newClientSheet)
                .then(response => {
                    if (response.data.success) {
                        this.newClientSheet = {};
                        location.pathname="/salesRep/client_sheets.html";
                    }
                });
        }
    }
});