Vue.createApp({
    data() {
      return {
        loading: true,
        errored: false,
        clients:[], 
            name:"",
            last_name:"",
            email:"",
        clientsREST:[]
      }
    },
    
    created(){
        axios.get('/rest/clients')
    
            .then(response => {
            this.clients = response.data._embedded.clients
            this.clientsREST = response.data
            })
            .catch(error => {
                console.log(error)
                this.errored = true
              })
              .finally(() => this.loading = false)
    },
    methods:{
    sendData(){
        axios.post('/rest/clients',{
            firstName:this.name,
            lastName:this.last_name,
            email:this.email
        })

    },
    deleteClient(client){
        if(confirm(`Do you want to delete the client ${client.firstName} ${client.lastName}?`))
            axios.delete(client._links.self.href)
            // location.reload()
            console.log(client._links.self.href);
        }
       

},
computed: {
    isDisabled () {
        if (this.name.length > 3 && this.last_name.length > 3 && this.email.length > 5 )
        {
            return false;
        } else {
            return true;
        }
      }
  }
}).mount('#app')