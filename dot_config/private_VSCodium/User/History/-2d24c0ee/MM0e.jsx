import { useState } from 'react';
import './App.css'

function handleSubmit(e){
  e.preventDefault();
  console.log(`${email}`);
}
function App() {
  const [email, setEmail] = useState("")
  const [passwd, setPasswd] = useState("")

  return (
    <form onSubmit={handleSubmit}>
      <div className='main-container'>
        <h2>Faça o seu login</h2>
        <hr/>
        <label htmlFor="email">Seu e-mail*</label>
        <input type="email" id='email' placeholder='Digite seu email' />
        <label htmlFor="senha">Sua senha*</label>
        <input type="password" id='senha' placeholder='Digite sua senha' onChange={()=>{setEmail(e.target.value)}}/>
        <div>
          <input type="checkbox" id='lembrar'/>
          <label htmlFor="lembrar">Lembrar-me</label>
        </div>
        <button type="submit" >Entrar</button>
        <div className='center'>
          <span>Esqueceu sua senha? <a href="#">Clique aqui!</a></span>
        </div>
      </div> 
    </form>
  )
}

export default App
