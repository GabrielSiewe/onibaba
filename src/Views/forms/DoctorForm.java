/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views.forms;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;

import databaseObjects.beans.PersonMVC.DoctorModel;
import databaseObjects.beans.PersonMVC.NurseModel;
import databaseObjects.beans.PersonMVC.PersonController;
import Views.Login;
import Views.PopUp;
import Views.Home.*;
import Views.lists.NurseLists;

/**
 *
 * @author xuelixiao
 */
public class DoctorForm extends javax.swing.JFrame {
	
	PersonController doctorController;
	JFrame current;
    /**
     * Creates new form addNurse
     */
    public DoctorForm() {
        initComponents();
        current = this;
    }
    
    /**
     * Creates new form addNurse
     */
    public DoctorForm(PersonController doctorController) {
    	this.doctorController =  doctorController;
        initComponents();
        current = this;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel3 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jTextField9 = new javax.swing.JTextField();
        jTextField9.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextField9.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextField9.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextField9.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        nurseInfo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField1.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextField1.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextField1.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextField1.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        jTextField2 = new javax.swing.JTextField();
        jTextField2.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextField2.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextField2.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextField2.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        jTextField3 = new javax.swing.JTextField();
        jTextField3.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextField3.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextField3.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextField3.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        jTextField4 = new javax.swing.JTextField();
        jTextField4.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextField4.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextField4.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextField4.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField5.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextField5.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextField5.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextField5.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        
        jTextField7 = new javax.swing.JTextField();
        jTextField7.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextField7.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextField7.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextField7.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        
        jTextField8 = new javax.swing.JTextField();
        jTextField8.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextField8.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextField8.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextField8.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextArea1.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextArea1.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextArea1.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextArea1.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jTextArea2.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextArea2.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextArea2.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextArea2.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        
        jLabel12 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField10.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextField10.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextField10.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextField10.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        jTextField6 = new javax.swing.JTextField();
        jTextField6.addFocusListener(new java.awt.event.FocusListener() {
            public void focusGained(java.awt.event.FocusEvent evt) {
            	jTextField6.setForeground(new java.awt.Color(0, 0, 0));
            	if(doctorController == null || doctorController.getNurse() == null) {
            		jTextField6.setText("");
            	}
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
            	jTextField6.setForeground(new java.awt.Color(153, 153, 153));
            }
        });
        jLabel25 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));
        if (doctorController == null) {
        	jButton8.setText(new Date().toString());
        } else {
        	jButton8.setText("Log out");
        	jButton8.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	new Login().setVisible(true);
                	dispose();
                }
            });
        	jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	ConcurrentHashMap<String, String> attributes = new ConcurrentHashMap<String, String>();
                	try {
                			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	                		attributes.put("first_name",jTextField1.getText());
	                    	attributes.put("last_name",jTextField2.getText());
	                    	attributes.put("ssn", jTextField3.getText());
	                    	attributes.put("allergies",jTextField4.getText());
	                    	attributes.put("gender",jTextField5.getText());
	                    	attributes.put("phone",jTextField7.getText());
	                    	attributes.put("email",jTextField8.getText());
	                    	attributes.put("education",jTextArea1.getText());
	                    	attributes.put("experience",jTextArea2.getText());
	                    	attributes.put("salary",jTextField10.getText());
	                    	attributes.put("birthday", jTextField6.getText().trim());
	                    	attributes.put("title","doctor");
	                    	attributes.put("updated_at", formatter.format(new Date()));
		                    doctorController.updateDoctor(attributes);
	                		doctorController.addToPrevious(current);
	                		new DoctorHome(doctorController).setVisible(true);
                	} catch (Exception e) {
                		PopUp pop = new PopUp();
                		pop.setText(e.getMessage());
                		pop.setVisible(true);
                	}
                }
                
            });
        	
        	jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	doctorController.addToPrevious(current);
                	new DoctorHome(doctorController).setVisible(true);
                }
            });
        	jButton8.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	new Login().setVisible(true);
                	dispose();
                }
            });
        	jButton9.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	doctorController.back(current).setVisible(true);
                }
            });
        	jButton10.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	doctorController.addToPrevious(current);
                	new DoctorHome(doctorController).setVisible(true);
                }
            });
        	jButton11.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                }
            });
        	jButton12.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                	doctorController.forward(current).setVisible(true);
                }
            });
        }
        

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/leftArrow.png"))); // NOI18N
        

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/home.png"))); // NOI18N
        

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/search.png"))); // NOI18N
        

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/rightArrow.png"))); // NOI18N
        

        jTextField9.setForeground(new java.awt.Color(153, 153, 153));
        jTextField9.setText("Search");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel1.setText("First Name");

        jLabel2.setText("Last Name");

        jLabel3.setText("SSN");

        jLabel4.setText("Allergies");
        jTextField1.setForeground(new java.awt.Color(153, 153, 153));
        if (doctorController == null ) {
            jTextField1.setText("First Name");
            jTextField2.setText("Last Name");
            jTextField3.setText("123-45-6789");
            jTextField4.setText("Nurse Allergies");
            jTextField5.setText("Female/Male");
            jTextField7.setText("123-456-7890");
            jTextField8.setText("email@domainname.com");
            jTextArea1.setText("Enter the education of the\nnurse here.");
            jTextArea2.setText("Enter the experience of the \nnurse here. ");
            jTextField10.setText("0.00");
            jTextField6.setText("12/31/2000");
            jLabel25.setText("Add Nurse");
            jButton1.setText("Add");
        } else {
        	DoctorModel doctor = doctorController.getDoctor();
            jTextField1.setText(doctor.getFirst_name());
            jTextField2.setText(doctor.getLast_name());
            jTextField3.setText(doctor.getSsn());
            jTextField4.setText("Allergies");
            jTextField5.setText(doctor.getGender());
            jTextField7.setText(doctor.getPhone());
            jTextField8.setText(doctor.getEmail());
            jTextArea1.setText(doctor.getEducation());
            jTextArea2.setText(doctor.getExperience());
            jTextField10.setText(doctor.getSalary()+"");
            jTextField6.setText(doctor.getBirthday().toString());
            jLabel25.setText("Update Doctor");
            jButton1.setText("Update");
        }
        
        jTextField2.setForeground(new java.awt.Color(153, 153, 153));
        jTextField3.setForeground(new java.awt.Color(153, 153, 153));
        jTextField4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Birthday");
        jLabel6.setText("Phone");
        jLabel7.setText("Email");
        jLabel9.setText("Gender");
        jTextField5.setForeground(new java.awt.Color(153, 153, 153));
        

        jTextField7.setForeground(new java.awt.Color(153, 153, 153));
        

        jTextField8.setForeground(new java.awt.Color(153, 153, 153));
        

        jLabel10.setText("Education");

        jLabel11.setText("Experience");

        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(153, 153, 153));
        jTextArea1.setRows(5);
       
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setForeground(new java.awt.Color(153, 153, 153));
        jTextArea2.setRows(5);
        
        jScrollPane2.setViewportView(jTextArea2);

        jLabel12.setText("Salary");

        jTextField10.setForeground(new java.awt.Color(153, 153, 153));
        

        jTextField6.setForeground(new java.awt.Color(153, 153, 153));
        
        

        jLabel25.setFont(new java.awt.Font("Krungthep", 1, 24)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 153, 255));
        

        javax.swing.GroupLayout nurseInfoLayout = new javax.swing.GroupLayout(nurseInfo);
        nurseInfo.setLayout(nurseInfoLayout);
        nurseInfoLayout.setHorizontalGroup(
            nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nurseInfoLayout.createSequentialGroup()
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(nurseInfoLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addGroup(nurseInfoLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(4, 4, 4))
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField4)
                            .addComponent(jTextField8)
                            .addComponent(jTextField7)
                            .addComponent(jTextField6)
                            .addComponent(jTextField2)
                            .addComponent(jTextField10)
                            .addComponent(jTextField1)
                            .addComponent(jTextField5)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(nurseInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        nurseInfoLayout.setVerticalGroup(
            nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, nurseInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addGap(43, 43, 43)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(nurseInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        
        jPanel1.add(jButton1);

        jButton2.setText("Cancel");
        jPanel1.add(jButton2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(nurseInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(26, 26, 26))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(nurseInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DoctorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DoctorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DoctorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoctorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DoctorForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JPanel nurseInfo;
    // End of variables declaration//GEN-END:variables
}
