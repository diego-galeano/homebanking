Vue.createApp({
    data() {
      return {
        accounts: [],
        accountType: null,
        client: [],
        createData: null,
        isActive: false,
        isOpen: false,
        loans:[],
        showMov: false
      }
    },
    created(){

      axios.get('/api/clients/current'/*,{headers:{'accept':'application/xml'}}*/)
      .then(res=> {
       this.client=res.data
       this.accounts=res.data.accounts.filter(account => account.active == true)
       this.loans = res.data.loans;
      })
},
    methods:{
      logout(){
        axios.post('/api/logout')
        .then(response => console.log('signed out!!!'))
        .then(response=>window.location.href = "index.html")
    },
    createAccount() {
      
      swal({
      
        title: '¿Desea crear una nueva cuenta?',
        text: `CUENTA TIPO: ${this.accountType == 'AHORRO' ? 'CAJA DE AHORRO': 'CUENTA CORRIENTE'}`,
        icon: 'warning',
       buttons: true,
        
      }).then((isConfirmed) => {
        if (isConfirmed) {
          axios.post('/api/clients/current/accounts', `accountType=${this.accountType}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
          .catch(res => swal(res.response.data,""))
          swal("Transacción completada",{
            icon: "success"
          })
          .then(()=>location.reload());
        }
      })
  
      
    }, 
    applyLoan(){
      window.location.href = "loan-application.html";
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
              .then(res => swal(
              'Cuenta eliminada con éxito!',
                  {icon:'success'},))
                  .catch(res => swal(res.response.data))
                  .then(() => location.reload())
              }
          })       
      }
      
  }
  }).mount('#app')