var app = new Vue({
    el: '#app',
    data() {
        return {
            productSheet : {},
            newProductSheet : {}
        }
    },
    mounted() {
        this.getId();
    },
    methods: {
        getId() {

            let url = new URL(window.location);
            let productSheetId = url.searchParams.get('productSheetId');

            axios.get('/salesRep/productsheet?productSheetId='+productSheetId)
                .then(response => {
                    this.productSheet = response.data.data;
                    this.newProductSheet = {...this.productSheet};
                });
        }
    }
});