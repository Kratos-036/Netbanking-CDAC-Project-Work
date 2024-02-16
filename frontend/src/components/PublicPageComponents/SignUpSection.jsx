import React from "react";
import styles from "./SignUpSection.module.css";
import { Link as ScrollLink } from "react-scroll";

const SignUpSection = () => {
  return (
    <section id="signup" className={`signup py-3 ${styles.signup}`}>
      <div className="row align-items-center container my-3 mx-auto">
        <div
          className={`text col-lg-6 col-md-6 col-12 pt-5 pb-5 ${styles.text}`}
        >
          <h6>JOIN OUR TEAM</h6>
          <h2>Creating an account is extremely easy</h2>
          <p>
            Lorem ipsum dolor, sit amet consectetur adipisicing elit. Expedita,
            laboriosam? Molestias, quaerat? Quasi magni adipisci itaque beatae
            quo, sequi ipsa aspernatur natus mollitia non suscipit dolor. Unde
            nisi enim nobis?
          </p>
          <ScrollLink
            to="desired-section-id" // Replace with the actual section ID to scroll to
            spy={true}
            smooth={true}
            offset={-70} // Adjust offset if needed
            duration={500} // Duration of the scroll animation
            className={styles.startNow}
          >
            Start Now
          </ScrollLink>
        </div>
        <div className={`img col-lg-6 col-md-6 col-12 pt-5 pb-5 ${styles.img}`}>
          <img
            className="img-fluid"
            src="../../assets/images/4.png"
            alt="Sign Up Section"
          />
        </div>
      </div>
    </section>
  );
};

export default SignUpSection;