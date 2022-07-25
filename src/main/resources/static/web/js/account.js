Vue.createApp({
    data() {
      return {
        client:[],
        account: [],
        transactions: null,
        isOpen: false
      }
    },
    created(){
        const urlParams = new URLSearchParams(window.location.search);
        const paramId = urlParams.get('id');
          axios.get(`/api/accounts/${paramId}`)      
          .then(res=> {
          this.account = res.data
           this.transactions=res.data.transactions 
        })
        .catch(res => console.log(res.response.data,"error")) 
        axios.get('/api/clients/current'/*,{headers:{'accept':'application/xml'}}*/)
        .then(res=> {
         this.client=res.data
        })
    },
    methods:{
      logout(){
        axios.post('/api/logout')
        .then(response => console.log('signed out!!!'))
        .then(response=>window.location.href = "index.html")
    },
    deleteAccount(account){
      swal({
          title: '¿Desea eliminar esta cuenta?',
          text: `Numero: ${account.number} \n Saldo: ${account.balance}`,
          icon: 'warning',
          buttons: true,
          dangerMode: true,
      }).then((isConfirmed) => {
          if (isConfirmed) {
              axios.put('/api/clients/current/deleteAccount',
              "number="+account.number,
              {headers:{'content-type':'application/x-www-form-urlencoded'}})
              .catch(res => swal(console.log(res.response.data)))
              .then(res => swal(
              'Cuenta eliminada con éxito!',
                  {icon:'success'},))
              // .then(() => location.reload())
          }
      })       
  }
    },
    computed:{
      saldo(){
        let result = 2;
        return result;
      }
    }
  }).mount('#app')
