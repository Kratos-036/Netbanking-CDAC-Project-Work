// CustomerTopNavigationBar.jsx

import React from "react";
import { Link } from "react-router-dom";
import { Badge, IconButton, InputBase, useTheme } from "@mui/material";
import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined";
import LightModeOutlinedIcon from "@mui/icons-material/LightModeOutlined";
import NotificationsOutlinedIcon from "@mui/icons-material/NotificationsOutlined";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";
import PersonOutlinedIcon from "@mui/icons-material/PersonOutlined";
import SearchIcon from "@mui/icons-material/Search";
import "./CustomerTopNavigationBar.css"; // CSS file

function CustomerTopNavigationBar() {
  const theme = useTheme();

  return (
    <div className="customer-top-navbar-container">
      {/* Logo and Navigation Links */}
      <div className="logo-container">
        <span className="logo">Aarna Bank</span>
        <div className="nav-links-container">
          <Link to="/Customer/Account/ViewAccountBalance" className="nav-link">
            Home
          </Link>
          <Link to="/Customer/OtherServices/OffersAvailableForMe31" className="nav-link">
            Offers Available
            </Link>
          <Link to="/Customer/OtherServices/NetBankingTutorials38" className="nav-link">
            Netbanking Tutorials
            </Link>
          <Link to="/Customer/OtherServices/ContactUs37" className="nav-link">
            Contact Us
            </Link>
        </div>
      </div>

      {/* Search Bar */}
      <div className="search-bar">
        <InputBase placeholder="Search" className="search-input" />
        <IconButton type="button" className="search-icon">
          <SearchIcon />
        </IconButton>
      </div>

      {/* User Icons */}
      <div className="user-icons-container">
        <IconButton className="user-icon">
          {theme.palette.mode === "dark" ? (
            <DarkModeOutlinedIcon />
          ) : (
            <LightModeOutlinedIcon />
          )}
        </IconButton>
        <IconButton className="user-icon">
          <Badge variant="dot" color="secondary">
            <NotificationsOutlinedIcon />
          </Badge>
        </IconButton>
        <IconButton className="user-icon">
          <SettingsOutlinedIcon />
        </IconButton>
        <IconButton className="user-icon">
          <PersonOutlinedIcon />
        </IconButton>
      </div>
    </div>
  );
}

export default CustomerTopNavigationBar;
