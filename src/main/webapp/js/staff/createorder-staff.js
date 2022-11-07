var app = new Vue({
    el: '#app',
    data() {
        return {
            orders: [],
            products: [],
            newProduct: {},
            newOrder: {productSheets:[]},
            clients: [],
            clientSelected: 0
        }
    },
    mounted() {
        axios.get('/salesRep/orders')
            .then(response => {
                this.orders = response.data.data;
            });
        axios.get('/salesRep/productsheets')
            .then(response => {
                this.products = response.data.data;
                console.log(this.products)
            });
        axios.get('/salesRep/client_sheets')
            .then(response => {
                this.clients = response.data.data;
            });
    },
    methods: {
        addProduct(event, newProduct) {
            const checked = event.target.checked;
            if (checked) {
                this.newOrder.productSheets.push(newProduct);
            }
            else {
                const index = this.newOrder.productSheets.indexOf(newProduct);
                this.newOrder.productSheets.splice(index, 1);
            }
            console.log(this.newOrder);
        },
        makeOrder() {
            axios.post('/salesRep/createorder/create', this.newOrder)
                .then(response => {
                    if (response.data.success) {
                        this.newOrder = {productSheets:[]};
                        this.newProduct = {};


                        axios.get('/salesRep/orders')
                            .then(response => {
                                this.orders = response.data.data;
                                location.pathname="/salesRep/orders.html";
                            });
                    }
                })
        }
    }
});