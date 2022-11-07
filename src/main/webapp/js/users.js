var app = new Vue({
    el: '#app',
    data() {
        return {
            users : []
        }
    },
    mounted() {
        this.loadUsers();
    },
    methods: {
        loadUsers() {
            axios.get('/admin/users')
                .then(response => {
                    this.users = response.data.data;
                });
        },

        removeUser(user) {
            axios.post('/admin/users/remove', user)
                .then(response => {
                    if (response.data.success) {
                        this.loadUsers();
                    }
                })
        },

        modifyUser(user) {
            axios.post('/admin/users/modify', user)
                .then(response => {
                    if (response.data.success) {
                        this.loadUsers();
                    }
                })
        }
    }
});