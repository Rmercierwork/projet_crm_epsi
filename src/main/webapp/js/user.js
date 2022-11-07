var app = new Vue({
    el: '#app',
    data() {
        return {
            user : {}
        }
    },
    mounted() {
        let url = new URL(window.location);
        let userLogin = url.searchParams.get('userLogin');

        axios.get('/admin/user?userLogin='+userLogin)
            .then(response => {
                this.user = response.data.data;
                this.newUser = Object.assign({},this.user);
            });
    },
    methods: {

    }
});