var app = new Vue({
    el: '#app',
    data() {
        return {
            order : {}
        }
    },
    mounted() {
        this.loadOrder();
    },
    methods: {
        loadOrder() {

            let url = new URL(window.location);
            let orderId = url.searchParams.get('orderId');
            axios.get('/salesRep/order?orderId='+orderId)
                .then(response => {
                    this.order = response.data.data;
                });
        },

        removeProduct(product) {

            let url = new URL(window.location);
            let orderId = url.searchParams.get('orderId');
            axios.post('/salesRep/order/remove?orderId='+orderId+"&productId="+product.id)
                .then(response => {
                    if (response.data.success) {
                        this.loadOrder();
                    }
                })
        },
    }
});