var app = new Vue({
    el: '#app',
    data() {
        return {
            newProductSheet : {}
        }
    },
    mounted() {
    },
    methods: {
        createProductSheet() {
            axios.post('/staff/createproductsheet/create', this.newProductSheet)
                .then(response => {
                    if (response.data.success) {
                        this.newProductSheet = {};
                        location.pathname="staff/productsheets.html";
                    }
                });
        }
    }
});