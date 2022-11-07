var app = new Vue({
    el: '#app',
    data() {
        return {
            productSheets : []
        }
    },
    mounted() {
        this.loadProductSheets();
    },
    methods: {
        loadProductSheets() {
            axios.get('/salesRep/productsheets')
                .then(response => {
                    this.productSheets = response.data.data;
                });
        }
    }
});