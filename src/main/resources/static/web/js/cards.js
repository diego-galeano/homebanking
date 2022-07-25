Vue.createApp({
    data() {
      return {
        client: [],
        accounts: [],
        isActive: false,
        debitCards:[],
        creditCards:[],
      }
    },
    created(){
      axios.get('/api/clients/current')
      .then(res=> {
       this.client=res.data
       this.debitCards =  res.data.cards.filter(e => e.cardType == "DEBIT" && e.active )
       this.creditCards = res.data.cards.filter(e => e.cardType == "CREDIT" && e.active )
      })
},
    methods:{
      createCard(){
        window.location.href = "create-cards.html"
      },
      deleteCard(cardNumber){
        swal({
          title: '¿Desea eliminar la tarjeta?',
          text: `Tarjeta nro: ${cardNumber}`,
          icon: 'warning',
          buttons: true,
          dangerMode: true,
        }).then((isDeleted) => {
          if (isDeleted) {
            axios.put('/api/clients/current/cards', `cardNumber=${cardNumber}`, {headers:{'content-type':'application/x-www-form-urlencoded'}})
            swal(
             'La operación se realizó con éxito!',
              {icon:'success'})
          .then(() => location.reload())
          .catch(res => swal(res.response.data,"error"))
          }
        })
        
      },
      isExpired(cardDate){
        const nowDate = new Date()
        if(cardDate < nowDate)
        return true;
        else
        return false;
      },
      cancel(){
        window.location.href = "cards.html"
    }, 
      logout(){
        axios.post('/api/logout')
        .then(res => console.log('signed out!!!'))
        .then(res =>window.location.href = "index.html")
    }
  }
   
}).mount('#app')