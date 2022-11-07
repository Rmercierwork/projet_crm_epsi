var app = new Vue({
    el: '#app',
    data() {
        return {
            orders : []
        }
    },
    mounted() {
        this.loadOrder();
    },
    methods: {
        loadOrder() {
            axios.get('/salesRep/orders')
                .then(response => {
                    this.orders = response.data.data;
                });
        },

        removeOrder(orders) {
            axios.post('/salesRep/orders/remove', orders)
                .then(response => {
                    if (response.data.success) {
                        this.loadOrder();
                    }
                })
        },
    }
});