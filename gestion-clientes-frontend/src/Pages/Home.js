import React from "react";

const Home = () => {
  return (
    <div style={styles.container}>
      <h1 style={styles.title}>Página Principal</h1>
      <p style={styles.text}>Bienvenido al sistema de Recursos Humanos</p>
    </div>
  );
};

const styles = {
  container: {
    padding: "40px",
  },
  title: {
    fontSize: "32px",
    marginBottom: "10px",
    fontWeight: "bold",
  },
  text: {
    fontSize: "18px",
    color: "#444",
  },
};

export default Home;
