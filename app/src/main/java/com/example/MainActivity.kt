package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.InstallMobile
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SmartButton
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        MainScreen()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
  val context = LocalContext.current
  var userName by remember { mutableStateOf("Desenvolvedor") }
  var tapCount by remember { mutableIntStateOf(0) }
  var isFavorite by remember { mutableStateOf(false) }
  var showHelpModal by remember { mutableStateOf(false) }

  val scrollState = rememberScrollState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "Primeiro APK",
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
            )
            Text(
              text = "Guia & Demonstração Nativa",
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        },
        actions = {
          IconButton(
            onClick = { showHelpModal = true },
            modifier = Modifier.testTag("help_icon_button")
          ) {
            Icon(
              imageVector = Icons.Outlined.HelpOutline,
              contentDescription = "Ajuda para Baixar APK"
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface,
          titleContentColor = MaterialTheme.colorScheme.onSurface
        )
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = { showHelpModal = true },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
          .padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
          .testTag("fab_download_guide")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Download,
            contentDescription = "Como Baixar APK"
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Como Baixar APK",
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(scrollState)
        .padding(horizontal = 20.dp, vertical = 12.dp),
      verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
      // 1. HERO BANNER CARD
      HeroBannerCard(userName = userName)

      // 2. INTERACTIVE DEMO CARD
      InteractiveDemoCard(
        userName = userName,
        onNameChange = { userName = it },
        tapCount = tapCount,
        onIncrementTap = { tapCount++ },
        onResetTap = { tapCount = 0 },
        isFavorite = isFavorite,
        onToggleFavorite = {
          isFavorite = !isFavorite
          Toast.makeText(
            context,
            if (isFavorite) "App marcado como Favorito! ❤️" else "Favorito removido",
            Toast.LENGTH_SHORT
          ).show()
        },
        onShowToast = {
          Toast.makeText(
            context,
            "🎉 Parabéns $userName! Este é um app Android real rodando no seu dispositivo!",
            Toast.LENGTH_LONG
          ).show()
        }
      )

      // 3. TUTORIAL CARD: PASSO A PASSO APK
      ApkTutorialCard(
        onOpenModal = { showHelpModal = true }
      )

      // 4. SPECS & PACKAGE INFO CARD
      PackageSpecsCard()

      Spacer(modifier = Modifier.height(72.dp))
    }
  }

  // DIALOG / MODAL DE INSTRUÇÕES DE DOWNLOAD
  if (showHelpModal) {
    AlertDialog(
      onDismissRequest = { showHelpModal = false },
      icon = {
        Icon(
          imageVector = Icons.Default.InstallMobile,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(36.dp)
        )
      },
      title = {
        Text(
          text = "Como Gerar e Baixar o APK",
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center
        )
      },
      text = {
        Column(
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          Text(
            text = "Siga estes passos simples no painel do AI Studio para obter seu instalador `.apk`:",
            style = MaterialTheme.typography.bodyMedium
          )

          StepItem(
            number = "1",
            title = "Painel Superior",
            description = "Localize a barra de ferramentas no topo da janela do AI Studio."
          )

          StepItem(
            number = "2",
            title = "Exportar Projeto",
            description = "Clique no menu de opções / engrenagem e selecione 'Export / Download APK' ou 'Download ZIP'."
          )

          StepItem(
            number = "3",
            title = "Instalar no Android",
            description = "Envie o arquivo .apk para seu celular, abra-o e autorize a instalação de fontes desconhecidas."
          )
        }
      },
      confirmButton = {
        Button(
          onClick = { showHelpModal = false },
          modifier = Modifier.testTag("close_guide_dialog")
        ) {
          Text("Entendido!")
        }
      }
    )
  }
}

@Composable
fun HeroBannerCard(userName: String) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("hero_banner_card"),
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = Color.Unspecified)
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          brush = Brush.horizontalGradient(
            colors = listOf(
              Color(0xFF4F46E5),
              Color(0xFF06B6D4)
            )
          )
        )
        .padding(20.dp)
    ) {
      Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Surface(
            color = Color.White.copy(alpha = 0.25f),
            shape = RoundedCornerShape(50)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "APK Prontinho para Exportar",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Icon(
            imageVector = Icons.Default.Android,
            contentDescription = "Ícone do Android",
            tint = Color.White.copy(alpha = 0.9f),
            modifier = Modifier.size(36.dp)
          )
        }

        Text(
          text = "Seu Primeiro App Android!",
          color = Color.White,
          fontSize = 24.sp,
          fontWeight = FontWeight.ExtraBold
        )

        Text(
          text = "Criado com Jetpack Compose & Kotlin. Praticamente pronto para rodar em qualquer smartphone Android.",
          color = Color.White.copy(alpha = 0.9f),
          fontSize = 14.sp,
          lineHeight = 20.sp
        )
      }
    }
  }
}

@Composable
fun InteractiveDemoCard(
  userName: String,
  onNameChange: (String) -> Unit,
  tapCount: Int,
  onIncrementTap: () -> Unit,
  onResetTap: () -> Unit,
  isFavorite: Boolean,
  onToggleFavorite: () -> Unit,
  onShowToast: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("interactive_demo_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    )
  ) {
    Column(
      modifier = Modifier.padding(18.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.SmartButton,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "Testar Interatividade Nativa",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }

      // Input de nome
      OutlinedTextField(
        value = userName,
        onValueChange = onNameChange,
        label = { Text("Seu Nome de Desenvolvedor") },
        leadingIcon = {
          Icon(
            imageVector = Icons.Default.PhoneAndroid,
            contentDescription = null
          )
        },
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("name_input_field"),
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = MaterialTheme.colorScheme.surface,
          unfocusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp)
      )

      // Contador de Toques
      Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(
          width = 1.dp,
          color = MaterialTheme.colorScheme.outlineVariant
        ),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text(
              text = "Toques de Teste",
              style = MaterialTheme.typography.labelMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "$tapCount cliques",
              style = MaterialTheme.typography.headlineSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary
            )
          }

          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            if (tapCount > 0) {
              IconButton(
                onClick = onResetTap,
                modifier = Modifier.testTag("reset_counter_button")
              ) {
                Icon(
                  imageVector = Icons.Default.Refresh,
                  contentDescription = "Zerar Contador"
                )
              }
            }

            Button(
              onClick = onIncrementTap,
              modifier = Modifier.testTag("increment_counter_button")
            ) {
              Text("Toque Aqui!")
            }
          }
        }
      }

      // Botões de ação adicionais
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = onShowToast,
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
          ),
          modifier = Modifier
            .weight(1f)
            .testTag("show_toast_button")
        ) {
          Icon(
            imageVector = Icons.Default.Send,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text("Testar Notificação")
        }

        IconButton(
          onClick = onToggleFavorite,
          modifier = Modifier
            .clip(CircleShape)
            .background(
              if (isFavorite) MaterialTheme.colorScheme.primaryContainer
              else MaterialTheme.colorScheme.surface
            )
            .testTag("favorite_toggle_button")
        ) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Favoritar App",
            tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
          )
        }
      }
    }
  }
}

@Composable
fun ApkTutorialCard(onOpenModal: () -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("apk_tutorial_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = androidx.compose.foundation.BorderStroke(
      width = 1.dp,
      color = MaterialTheme.colorScheme.outlineVariant
    )
  ) {
    Column(
      modifier = Modifier.padding(18.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.InstallMobile,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = "Passo a Passo para Gerar o APK",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold
        )
      }

      Text(
        text = "Um arquivo APK (Android Package) é o executável do seu aplicativo. Veja como obtê-lo agora mesmo:",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )

      TutorialStepRow(
        stepNumber = "1",
        title = "Exportar no AI Studio",
        description = "Clique nas opções do projeto no menu superior e escolha a opção Exportar/Download APK."
      )

      TutorialStepRow(
        stepNumber = "2",
        title = "Enviar para o Smartphone",
        description = "Mova o arquivo .apk para seu celular via cabo USB, WhatsApp, Telegram ou Google Drive."
      )

      TutorialStepRow(
        stepNumber = "3",
        title = "Instalar no Aparelho",
        description = "Toque no arquivo .apk no gerenciador de arquivos do celular e confirme a instalação."
      )

      TextButton(
        onClick = onOpenModal,
        modifier = Modifier
          .align(Alignment.End)
          .testTag("open_tutorial_dialog_button")
      ) {
        Text("Ver Guia Detalhado →")
      }
    }
  }
}

@Composable
fun TutorialStepRow(
  stepNumber: String,
  title: String,
  description: String
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalAlignment = Alignment.Top
  ) {
    Surface(
      color = MaterialTheme.colorScheme.primaryContainer,
      contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
      shape = CircleShape,
      modifier = Modifier.size(28.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = stepNumber,
          fontWeight = FontWeight.Bold,
          fontSize = 14.sp
        )
      }
    }

    Column(
      verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = 16.sp
      )
    }
  }
}

@Composable
fun PackageSpecsCard() {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("package_specs_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    )
  ) {
    Column(
      modifier = Modifier.padding(18.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Info,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Detalhes Técnicos do APK",
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold
        )
      }

      SpecItem(label = "Application ID", value = "com.aistudio.meuprimeiroapk.xypq")
      SpecItem(label = "Tecnologia", value = "Kotlin + Jetpack Compose")
      SpecItem(label = "SDK Mínimo", value = "Android 7.0 (API 24)")
      SpecItem(label = "Versão do App", value = "1.0.0 (Build Debug/Release)")
    }
  }
}

@Composable
fun SpecItem(label: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodySmall,
      fontWeight = FontWeight.SemiBold,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}

@Composable
fun StepItem(
  number: String,
  title: String,
  description: String
) {
  Row(
    horizontalArrangement = Arrangement.spacedBy(10.dp),
    verticalAlignment = Alignment.Top
  ) {
    Surface(
      color = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary,
      shape = CircleShape,
      modifier = Modifier.size(24.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Text(
          text = number,
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp
        )
      }
    }
    Column {
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}
