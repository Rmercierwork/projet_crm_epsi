var app = new Vue({
    el: '#app',
    data() {
        return {
            clientSheet : {},
            newClientSheet : {},
            orders : []
        }
    },
    mounted() {
        this.getId();
        this.loadOrders();
    },
    methods: {
        getId() {

            let url = new URL(window.location);
            let clientSheetId = url.searchParams.get('clientSheetId');

            axios.get('/salesRep/client_sheet?clientSheetId='+clientSheetId)
                .then(response => {
                    this.clientSheet = response.data.data;
                    this.newClientSheet = {...this.clientSheet};
                });
        },
        updateClientSheet() {
            axios.post('/salesRep/client_sheet/modify', this.newClientSheet)
                .then(response => {
                    if (response.data.success) {

                        this.getId();
                    }
                });
        },
        loadOrders() {
            let url = new URL(window.location);
            let clientSheetId = url.searchParams.get('clientSheetId');

            axios.get('/salesRep/client_sheet/orders?clientSheetId='+clientSheetId)
                .then(response => {
                    this.orders = response.data.data.map(order => ({
                        ...order,
                        displayDate:new Date(order.date).toLocaleDateString()
                    }));

                });
        },
        removeOrder(order) {
            axios.post('/salesRep/orders/remove', order)
                .then(response => {
                    if (response.data.success) {
                        this.loadOrders();
                    }
                })
        }
    }
});